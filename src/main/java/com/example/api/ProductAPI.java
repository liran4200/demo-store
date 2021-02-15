package com.example.api;


import com.example.entity.Product;
import com.example.service.ProductAlreadyExistsException;
import com.example.service.ProductNotFoundException;
import com.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @author Liran Yehudar
 */
@RestController()
public class ProductAPI {

    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(
            method= RequestMethod.POST,
            path="api/products",
            produces=MediaType.APPLICATION_JSON_VALUE,
            consumes= MediaType.APPLICATION_JSON_VALUE)
    public Product createProduct(@RequestBody Product newProduct){

        return this.productService.createProduct(newProduct);
    }

    @RequestMapping(method=RequestMethod.GET,
            path="api/products/{id}",
            produces= MediaType.APPLICATION_JSON_VALUE,
            consumes= MediaType.APPLICATION_JSON_VALUE)
    Product getProduct(@PathVariable Long id) throws ProductNotFoundException {
        return this.productService.retrieveProduct(id);
    }

    @RequestMapping(method=RequestMethod.GET,
            path="api/products",
            produces= MediaType.APPLICATION_JSON_VALUE,
            consumes= MediaType.APPLICATION_JSON_VALUE)
    List<Product> getAllProducts() {
        return this.productService.retrieveAllProducts();
    }

    @RequestMapping(
            method=RequestMethod.PUT,
            path="api/products/{id}",
            consumes=MediaType.APPLICATION_JSON_VALUE)
    public void updateProduct (
            @PathVariable Long id,
            @RequestBody Product product) throws ProductNotFoundException {
        this.productService.updateProduct(id, product);
    }

    @RequestMapping(
            method=RequestMethod.DELETE,
            path="api/products/{id}",
            consumes=MediaType.APPLICATION_JSON_VALUE)
    public void deleteProduct (@PathVariable Long id) throws ProductNotFoundException {
        this.productService.deleteProduct(id);
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handleSpecificException (ProductNotFoundException e) {
        return handleException(e);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorMessage handleSpecificException (ProductAlreadyExistsException e) {
        return handleException(e);
    }

    /**
     * This method create a error message to client.
     * @param  Exception e
     * @return ErrorMessage which contain message to client
     */
    private ErrorMessage handleException(Exception e) {
        String message = e.getMessage();
        if (message == null) {
            message = "There is no relevant message";
        }
        return new ErrorMessage(message);
    }

}
