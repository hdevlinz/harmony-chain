package com.tth.rating.controller;

import com.tth.commonlibrary.dto.APIResponse;
import com.tth.commonlibrary.dto.request.rating.RatingRequestCreate;
import com.tth.commonlibrary.dto.request.rating.RatingRequestUpdate;
import com.tth.commonlibrary.dto.response.rating.RatingResponse;
import com.tth.rating.service.RatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/ratings", produces = "application/json; charset=UTF-8")
public class APIRatingController {

    private final RatingService ratingService;

    @GetMapping
    public ResponseEntity<?> listRatingsOfSupplier(@RequestParam String supplierId,
                                                   @RequestParam(required = false, defaultValue = "1") int page,
                                                   @RequestParam(required = false, defaultValue = "5") int size) {
        return ResponseEntity.ok(this.ratingService.findRatingsBySupplierId(supplierId, page, size));
    }

    @PostMapping("/add")
    public ResponseEntity<?> createRating(@RequestBody @Valid RatingRequestCreate request) {
        RatingResponse rating = this.ratingService.createRating(request);

        return ResponseEntity.ok(APIResponse.<RatingResponse>builder().result(rating).build());
    }

    @PutMapping(path = "/{ratingId}")
    public ResponseEntity<?> updateRating(@PathVariable String ratingId, @RequestBody @Valid RatingRequestUpdate request) {
        RatingResponse rating = this.ratingService.updateRating(ratingId, request);

        return ResponseEntity.ok(APIResponse.<RatingResponse>builder().result(rating).build());
    }

    @DeleteMapping(path = "/{ratingId}")
    public ResponseEntity<?> deleteRating(@PathVariable String ratingId) {
        this.ratingService.deleteRating(ratingId);

        return ResponseEntity.noContent().build();
    }

}
