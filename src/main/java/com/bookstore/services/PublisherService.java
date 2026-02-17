package com.bookstore.services;

import com.bookstore.dtos.PublisherRecordDto;
import com.bookstore.exceptions.DataFormatWrongException;
import com.bookstore.models.PublisherModel;
import com.bookstore.repsitories.PublisherRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PublisherService {
    private final PublisherRepository publisherRepository;
    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    // -------------- FIND METHODS --------------
    public List<PublisherModel> getAllPublishers() {
        return publisherRepository.findAll();
    }

    public List<PublisherModel> getAllPublishersByContainingName(String name) {
        if (!StringUtils.hasText(name)) {
            throw new DataFormatWrongException("Data cannot be empty. Please verify the request content.");
        }

        List<PublisherModel> publishers = publisherRepository.findPublisherModelsByNameContainingIgnoreCase(name);

        if (publishers.isEmpty()) {
            throw new EntityNotFoundException("Publishers not found. Please check the provided title.");
        }

        return publishers;
    }

    public PublisherModel getPublisherById(UUID id) {
        if (id == null) {
            throw new DataFormatWrongException("The provided UUID can't be empty or null.");
        }

        return publisherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author with UUID: '" + id + "' not found."));
    }

    // -------------- SAVE/UPDATE/EXCLUDE METHODS --------------
    @Transactional
    public PublisherModel savePublisher(PublisherRecordDto publisherRecordDto) {
        if (!StringUtils.hasText(publisherRecordDto.name())) {
            throw new DataFormatWrongException("Data cannot be empty. Please verify the request content.");
        }

        if (publisherRecordDto.name().matches("\\d+")) {
            throw new DataFormatWrongException("The name cannot consist solely of numbers.");
        }

        Optional<PublisherModel> publisherToCompare = publisherRepository.findPublisherModelByName(publisherRecordDto.name());

        if (publisherToCompare.isPresent()) {
            throw new DataIntegrityViolationException("Already exists an publisher with this name. Please change the name of new publisher.");
        }

        PublisherModel newPublisher = new PublisherModel();
        newPublisher.setName(publisherRecordDto.name());

        return publisherRepository.save(newPublisher);
    }

    @Transactional
    public PublisherModel updatePublisher(UUID id, PublisherRecordDto publisherRecordDto) {
        if (!StringUtils.hasText(publisherRecordDto.name())) {
            throw new DataFormatWrongException("Data cannot be empty. Please verify the request content.");
        }

        if (publisherRecordDto.name().matches("\\d+")) {
            throw new DataFormatWrongException("The name cannot consist solely of numbers.");
        }

        PublisherModel publisherToUpdate = getPublisherById(id);
        Optional<PublisherModel> publisherToCompare = publisherRepository.findPublisherModelByName(publisherRecordDto.name());

        if (publisherToCompare.isPresent() && !publisherToCompare.get().getId().equals(publisherToUpdate.getId())) {
            throw new DataIntegrityViolationException("Already exists an publisher with this name. Please change the name to update this publisher.");
        }

        publisherToUpdate.setName(publisherRecordDto.name());
        return publisherRepository.save(publisherToUpdate);
    }

//    @Transactional
//    public void deletePublisher(UUID id) {
//        getPublisherById(id);
//        publisherRepository.deleteById(id);
//    }
}
