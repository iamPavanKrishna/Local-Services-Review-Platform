package com.localservicesreview.servicemanagementservice.controllers;

import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.localservicesreview.servicemanagementservice.dtos.CreateServiceRequestDto;
import com.localservicesreview.servicemanagementservice.dtos.CreateServiceResponseDto;
import com.localservicesreview.servicemanagementservice.dtos.GetServiceResponseDto;
import com.localservicesreview.servicemanagementservice.dtos.SearchServiceResponseDto;
import com.localservicesreview.servicemanagementservice.dtos.UploadPhotoResponseDto;
import com.localservicesreview.servicemanagementservice.services.ServiceService;

@RestController
@RequestMapping("/api/v1/services")
public class ServiceController {
    private ServiceService serviceService;
    public ServiceController(ServiceService serviceService){
        this.serviceService = serviceService;
    }

    @GetMapping("/hello")
    public ResponseEntity<String> sayHello(){
        return new ResponseEntity<>("Hello, your service is up!!", HttpStatus.OK);
    }

    //post service
    @PostMapping
    public ResponseEntity<CreateServiceResponseDto> createService(@RequestBody CreateServiceRequestDto requestDto) throws Exception {
        CreateServiceResponseDto responseDto = serviceService.createService(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    //get service
    @GetMapping("{id}")
    public ResponseEntity<GetServiceResponseDto> getService(@PathVariable("id") UUID serviceId){
        GetServiceResponseDto serviceResponseDto = serviceService.getService(serviceId);
        return new ResponseEntity<>(serviceResponseDto, HttpStatus.OK);
    }

    //get all services - internal
    @GetMapping
    public ResponseEntity<List<GetServiceResponseDto>> getAllServices(){
        List<GetServiceResponseDto> serviceResponseDto = serviceService.getAllServices();
        return new ResponseEntity<>(serviceResponseDto, HttpStatus.OK);
    }

    @GetMapping("/category")
    public ResponseEntity<List<GetServiceResponseDto>> getAllServicesByCategory(@RequestParam(value="category", required = true) String categoryName){
        List<GetServiceResponseDto> serviceResponseDtos = serviceService.getAllServiceByCategory(categoryName);
        return new ResponseEntity<>(serviceResponseDtos, HttpStatus.OK);
    }

    //search service
    @GetMapping("/search")
    public ResponseEntity<SearchServiceResponseDto> searchService(@RequestParam(value = "input", required = true) String input,
        @RequestParam(value="inputtype", required = true, defaultValue = "name") String inputtype,
        @RequestParam(value="categories", required = false) List<String> categoryNames,
        @RequestParam(value="ratings", required = false) List<Double> ratings)
    {
        SearchServiceResponseDto searchServiceResponseDto = serviceService.searchServices(input, inputtype, categoryNames, ratings);
        return new ResponseEntity<>(searchServiceResponseDto, HttpStatus.OK);
    }

    //add photo
    @PostMapping("{id}/photos")
    public ResponseEntity<UploadPhotoResponseDto> addPhotos(@PathVariable("id") UUID serviceId, @RequestParam("files") MultipartFile[] file, ModelMap modelMap){
        return null;
    }

}
