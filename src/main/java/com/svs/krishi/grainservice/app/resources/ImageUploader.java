package com.svs.krishi.grainservice.app.resources;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Path("/")
@Slf4j
public class ImageUploader {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ImageIdResult {
        private String imageId;
    }

    @GET
    @Path("/id")
    @Produces(MediaType.APPLICATION_JSON)
    public ImageIdResult getImageId() {
        return ImageIdResult.builder().imageId(UUID.randomUUID().toString()).build();
    }

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(final Request request) {
        log.info("Handling the file upload request.");
        final String imageId = UUID.randomUUID().toString();
        return Response.ok(imageId).build();
    }
}
