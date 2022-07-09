package com.tinie.Services.controllers;

import com.tinie.Services.repositories.BusinessDetailsRepository;
import com.tinie.Services.responses.BusinessDetailsResponse;
import com.tinie.Services.util.EnvConstants;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("api/v1")
public class BusinessDetailController {

    @Autowired
    private BusinessDetailsRepository businessDetailsRepository;
    @Autowired
    private EnvConstants envConstants;
    @Autowired
    private ModelMapper mapper;

    /**
     * Get a paginated list of nearby {@link BusinessDetailsResponse} given coordinates
     * @param httpServletRequest An object of type {@link HttpServletRequest} containing all the information about the request.
     * @return A {@link List} of {@link BusinessDetailsResponse}.
     * */
    @GetMapping("get-nearby-services")
    @ApiOperation(value = "Get list of Categories and associated Subcategories")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "RETRIEVAL SUCCESSFUL"),
            @ApiResponse(code = 204, message = "NO CONTENT"),
            @ApiResponse(code = 500, message = "RETRIEVAL FAILED")
    })
    public ResponseEntity<?> getNearestServices(HttpServletRequest httpServletRequest,
                                                @RequestParam(defaultValue = "1") Integer page,
                                                @RequestParam(defaultValue = "20") Integer size,
                                                @RequestParam Float latitude,
                                                @RequestParam Float longitude,
                                                @RequestParam Integer subCategoryId) {

        var businesses = businessDetailsRepository.getBusinessesWithinDistance(
                envConstants.getDefaultMaxDistanceKm(),
                size,
                (Math.abs(page) - 1) * size,
                latitude,
                longitude,
                subCategoryId
        );

        //noinspection unchecked
        var response = ((List<BusinessDetailsResponse>)
                mapper.map(businesses, new TypeToken<List<BusinessDetailsResponse>>() {}.getType()));
        return new ResponseEntity<>(response, response.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }
}
