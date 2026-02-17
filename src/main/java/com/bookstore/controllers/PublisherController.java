package com.bookstore.controllers;

import com.bookstore.dtos.PublisherRecordDto;
import com.bookstore.models.PublisherModel;
import com.bookstore.services.PublisherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("bookstore/publishers")
public class PublisherController {
    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping
    public ResponseEntity<List<PublisherModel>> getAllPublishers(@RequestParam(required = false) String name) {
        if (name != null) {
            return ResponseEntity.ok(publisherService.getAllPublishersByContainingName(name));
        }
        return ResponseEntity.status(HttpStatus.OK).body(publisherService.getAllPublishers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublisherModel> getPublisherByID(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(publisherService.getPublisherById(id));
    }

    @PostMapping
    public ResponseEntity<PublisherModel> save(@RequestBody PublisherRecordDto publisherRecordDto) {
        PublisherModel response = publisherService.savePublisher(publisherRecordDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PublisherModel> update(@PathVariable UUID id, @RequestBody PublisherRecordDto publisherRecordDto) {
        PublisherModel response = publisherService.updatePublisher(id, publisherRecordDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> delete(@PathVariable UUID id) {
//        publisherService.deletePublisher(id);
//        return ResponseEntity.status(HttpStatus.OK).body("Publisher with id '" + id + "' deleted successfully. \n NOTE: All books of this publisher were ");
//    }
}
