package com.bookstore.services;

import com.bookstore.models.PublisherModel;
import com.bookstore.repsitories.PublisherRepository;

import java.util.List;

public class PublisherService {
    private final PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public List<PublisherModel> getAllPublishers() {
        return publisherRepository.findAll();
    }
}
