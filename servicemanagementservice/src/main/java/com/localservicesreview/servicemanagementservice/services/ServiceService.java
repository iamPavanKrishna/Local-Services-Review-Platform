package com.localservicesreview.servicemanagementservice.services;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import com.localservicesreview.servicemanagementservice.dtos.CreateServiceRequestDto;
import com.localservicesreview.servicemanagementservice.dtos.CreateServiceResponseDto;
import com.localservicesreview.servicemanagementservice.dtos.GetServiceResponseDto;
import com.localservicesreview.servicemanagementservice.dtos.SearchServiceResponseDto;
import com.localservicesreview.servicemanagementservice.exceptions.CategoryNotFoundException;
import com.localservicesreview.servicemanagementservice.exceptions.NoPeriodDataExistsForService;
import com.localservicesreview.servicemanagementservice.exceptions.ServiceNotFoundException;
import com.localservicesreview.servicemanagementservice.models.Address;
import com.localservicesreview.servicemanagementservice.models.Category;
import com.localservicesreview.servicemanagementservice.models.Period;
import com.localservicesreview.servicemanagementservice.models.Service;
import com.localservicesreview.servicemanagementservice.models.ServiceState;
import com.localservicesreview.servicemanagementservice.repositories.CategoryRepo;
import com.localservicesreview.servicemanagementservice.repositories.PeriodRepo;
import com.localservicesreview.servicemanagementservice.repositories.ServiceRepo;
import com.localservicesreview.servicemanagementservice.thirdPartyClients.rating.RatingService;
import com.localservicesreview.servicemanagementservice.thirdPartyClients.reviews.ReviewService;

@org.springframework.stereotype.Service
public class ServiceService {
    private ServiceRepo serviceRepo;
    private CategoryRepo categoryRepo;
    private PeriodRepo periodRepo;
    private RatingService ratingService;
    private ReviewService reviewService;
    public ServiceService(ServiceRepo serviceRepo, CategoryRepo categoryRepo, PeriodRepo periodRepo, RatingService ratingService, ReviewService reviewService){
        this.serviceRepo = serviceRepo;
        this.categoryRepo = categoryRepo;
        this.periodRepo = periodRepo;
        this.ratingService = ratingService;
        this.reviewService = reviewService;
    }

    public CreateServiceResponseDto createService(CreateServiceRequestDto requestDto) {
        Service newService = new Service();
        newService.setName(requestDto.getName());
        newService.setPhoneNumber(requestDto.getPhoneNumber());
        newService.setEmail(requestDto.getEmailId());

        Address newAddress = new Address();
        newAddress.setAddressLine1(requestDto.getAddress().getAddressLine1());
        newAddress.setAddressLine2(requestDto.getAddress().getAddressLine2());
        newAddress.setCity(requestDto.getAddress().getCity());
        newAddress.setPostalCode(requestDto.getAddress().getPostalCode());
        newAddress.setCountry(requestDto.getAddress().getCountry());
        newService.setAddress(newAddress);

        String formattedAddress = newAddress.getAddressLine1()+", "+newAddress.getAddressLine2()+", "
            +newAddress.getCity()+" - "+newAddress.getPostalCode()+", "+newAddress.getCountry();

        newService.setFormattedAddress(formattedAddress);
        newService.setWebsite(requestDto.getWebsite());
        newService.setOwnerName(requestDto.getOwnerName());

        //categories
        List<Category> categories = new ArrayList<>();
        for(UUID catId: requestDto.getCategories()){
            Optional<Category> category = categoryRepo.findById(catId);
            if(!category.isPresent()){
                throw new CategoryNotFoundException("Service service: Category not found for ID: "+catId);
            }
            categories.add(category.get());
        }
        newService.setCategories(categories);

        newService.setGoogleMapLink(requestDto.getGoogleMapLink());
        newService.setServiceState(ServiceState.OPERATIONAL);
        newService.setPeriods(requestDto.getWeeklyOpenPeriods());
        newService.setPhotos(new ArrayList<>());
        newService.setTotalUserRatings(0l);
        newService.setReviews(new ArrayList<>());
        newService.setLocation(requestDto.getLocation());
        newService.setCreatorId(requestDto.getCreatorId());

        Service savedService = serviceRepo.save(newService);
        CreateServiceResponseDto serviceResponseDto = CreateServiceResponseDto.from(savedService);

        return serviceResponseDto;
    }

    public GetServiceResponseDto getService(UUID serviceId){
        Optional<Service> service = serviceRepo.findById(serviceId);
        if(!service.isPresent()){
            throw new ServiceNotFoundException("Service service: Service not found with id: "+serviceId);
        }
        //get ratings
        service.get().setRating(ratingService.getRatingsByServiceId(serviceId));
        service.get().setTotalUserRatings(ratingService.getTotalRatingsByServiceId(serviceId));

        //get reviews
        service.get().setReviews(reviewService.getReviewsByServiceId(serviceId));

        GetServiceResponseDto serviceResponseDto = GetServiceResponseDto.from(service.get());
        // open now
        serviceResponseDto.setOpenNow(true);// checkIfServiceOpen(serviceId) TODO
        return serviceResponseDto;
    }

    public List<GetServiceResponseDto> getAllServices(){
        List<Service> services = serviceRepo.findAll();
        return populateValuesForGetServiceResponseDto(services);
    }

    public List<GetServiceResponseDto> getAllServiceByCategory(String categoryName){
        List<Service> services = serviceRepo.findAllByCategories_name(categoryName);
        return populateValuesForGetServiceResponseDto(services);
    }

    public List<GetServiceResponseDto> getAllServiceByName(String name){
        List<Service> services = serviceRepo.findAllByNameContainingIgnoreCase(name);
        return populateValuesForGetServiceResponseDto(services);
    }

    public List<GetServiceResponseDto> getAllServiceByAddress(String address){
        List<Service> services = serviceRepo.findAllByFormattedAddressContainsIgnoreCase(address);
        return populateValuesForGetServiceResponseDto(services);
    }

    public List<GetServiceResponseDto> getAllServiceByCategoryAndRating(String categoryName, int rating){
        List<Service> services = serviceRepo.findAllByCategories_name_AndRatingEquals(categoryName, rating );
        // TODO handle if no services are returned
        return populateValuesForGetServiceResponseDto(services);
    }

    public SearchServiceResponseDto searchServices(String searchKeyword, String searchType, List<String> categoryNames, List<Double> ratings){
        if(!searchType.equals("name") && !searchType.equals("address")){
            throw new RuntimeException("Service service: Invalid inputtype. Possible values are 'name' and 'address'");
        }

        List<GetServiceResponseDto> serviceResponseDtos = new ArrayList<>();
        if((categoryNames == null && ratings == null) || (categoryNames.size() == 0 && ratings.size() == 0)){
            // search based on name and address
            if(searchType.equals("name")){
                serviceResponseDtos = populateValuesForGetServiceResponseDto(serviceRepo.findAllByNameContainingIgnoreCase(searchKeyword));
            } else if (searchType.equals("address")){
                serviceResponseDtos = populateValuesForGetServiceResponseDto(serviceRepo.findAllByFormattedAddressContainsIgnoreCase(searchKeyword));
            }
        } else if((categoryNames.size()>0 && ratings == null) || (categoryNames.size()>0 && ratings.size() == 0)){
            if(searchType.equals("name")){
                serviceResponseDtos = populateValuesForGetServiceResponseDto(serviceRepo.findAllByNameContainingIgnoreCaseAndCategories_nameIn(searchKeyword, categoryNames));
            } else if (searchType.equals("address")){
                serviceResponseDtos = populateValuesForGetServiceResponseDto(serviceRepo.findAllByFormattedAddressContainsIgnoreCaseAndCategories_nameIn(searchKeyword, categoryNames));
            }
        } else if((categoryNames == null && ratings.size()>0) || (categoryNames.size() == 0 && ratings.size()>0)){
            if(searchType.equals("name")){
                serviceResponseDtos = populateValuesForGetServiceResponseDto(serviceRepo.findAllByNameContainingIgnoreCaseAndRatingIn(searchKeyword, ratings));
            } else if (searchType.equals("address")){
                serviceResponseDtos = populateValuesForGetServiceResponseDto(serviceRepo.findAllByFormattedAddressContainsIgnoreCaseAndRatingIn(searchKeyword, ratings));
            }
        } else {
            if(searchType.equals("name")){
                serviceResponseDtos = populateValuesForGetServiceResponseDto(serviceRepo.findAllByNameContainingIgnoreCaseAndCategories_nameInAndRatingIn(searchKeyword, categoryNames, ratings));
            } else if (searchType.equals("address")){
                serviceResponseDtos = populateValuesForGetServiceResponseDto(serviceRepo.findAllByFormattedAddressContainsIgnoreCaseAndCategories_nameInAndRatingIn(searchKeyword, categoryNames, ratings));
            }
        }

        SearchServiceResponseDto searchServiceResponseDto = new SearchServiceResponseDto();
        searchServiceResponseDto.setTotalCount(serviceResponseDtos.size());
        searchServiceResponseDto.setServices(serviceResponseDtos);

        return searchServiceResponseDto;
    }

    private List<GetServiceResponseDto> populateValuesForGetServiceResponseDto(List<Service> services){
        List<GetServiceResponseDto> getServiceResponseDtos = new ArrayList<>();
        for(Service service : services){
            getServiceResponseDtos.add(GetServiceResponseDto.from(service));
        }
        return getServiceResponseDtos;
    }


    private boolean checkIfServiceOpen(UUID serviceId){
        LocalDateTime currentTime = LocalDateTime.now();
        DayOfWeek day = currentTime.getDayOfWeek();

        //getting value from database for day for service id
        Optional<List<Period>> periods =periodRepo.findAllByService_Id(serviceId); // TODO this query is resulting in error
        if(!periods.isPresent()){
            throw new NoPeriodDataExistsForService("Service service: no period information exists for service id: "+ serviceId);
        }
        for (Period period : periods.get()){
            int periodDay = period.getDay();
            if(periodDay == day.getValue()){
                // check if time range of period is within current time TODO
            }
        }
        return true;
    }

}
