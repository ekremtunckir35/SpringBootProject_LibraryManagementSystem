package com.tpe.controller;

import com.tpe.domain.Owner;
import com.tpe.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;



    //2-Find All Owners
    //http://localhost:8080/owners + GET
    @RequestMapping("/owners")
    @GetMapping
    public ResponseEntity<List<Owner>>getAllOwners(){
         List<Owner> owners=ownerService.getAll();
         return ResponseEntity.ok(owners);
    }

    

}
