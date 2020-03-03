package com.parcom.polls.model.variant;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(value = "/variants",produces = {MediaType.APPLICATION_JSON_VALUE})
@Api(tags="Variants")
@RequiredArgsConstructor
public class VariantController {

    private final VariantService variantService;

    @GetMapping
    @ApiOperation(value = "Get all variants")
    public List<Variant> all(@RequestParam Long idPoll){
        return variantService.all(idPoll);
    }

    @PostMapping
    @ApiOperation(value = "Add variant to poll")
    public Variant create(@Valid @RequestBody VariantDto variantDto,
                               BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        return variantService.create(variantDto);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update variant")
    public Variant update(@PathVariable Long id,@Valid @RequestBody VariantDto variantDto,
                          BindingResult bindingResult) throws BindException
    {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        return variantService.update(id,variantDto);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete variant")
    public void delete(@PathVariable Long id)
    {
        variantService.delete(id);
    }

}
