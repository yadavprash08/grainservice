package com.svs.krishi.grainservice.app.resources;

import com.google.gson.Gson;
import com.google.inject.Injector;
import com.svs.krishi.grainservice.app.di.InjectionFactory;
import com.svs.krishi.grainservice.app.ml.Classifiers;
import com.svs.krishi.grainservice.app.model.grain.GrainDimensions;
import com.svs.krishi.grainservice.app.model.grain.type.GrainType;
import com.svs.krishi.grainservice.app.model.grain.type.ParticleType;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.util.Objects;

/**
 * This is the classifier for analyzing wheat parameters.
 */
@Path("/wheat")
@Slf4j
public class WheatClassifier {

    @GET
    @Path("/classify")
    public Response classify(@QueryParam("area") final Double area,
        @QueryParam("parameter") final Double parameter,
        @QueryParam("major") final Double major,
        @QueryParam("minor") final Double minor,
        @QueryParam("solidity") final Double solidity,
        @Context final HttpServletRequest request) {
        try {
            Objects.requireNonNull(area, "area missing");
            Objects.requireNonNull(parameter, "parameter missing");
            Objects.requireNonNull(major, "major missing");
            Objects.requireNonNull(minor, "minor missing");
            Objects.requireNonNull(solidity, "solidity missing");
        } catch (NullPointerException npe) {
            return Response.status(Response.Status.BAD_REQUEST).entity(npe.getMessage()).build();
        }

        final String requestId = Objects.toString(request.getAttribute("RequestId"));
        log.info("Extracting request id {} from request", requestId);
        final GrainDimensions grainDimension = GrainDimensions.builder()
            .particleId(requestId)
            .area(area)
            .perimeter(parameter)
            .major(major)
            .minor(minor)
            .solidity(solidity)
            .build();
        final Injector injector = InjectionFactory.getInstance().getInjector();
        final Classifiers classifier = injector.getInstance(Classifiers.class);
        final GrainDimensions result = classifier.classify(grainDimension);
        final Gson gson = injector.getInstance(Gson.class);
        return Response.ok().entity(gson.toJson(result)).build();
    }
}
