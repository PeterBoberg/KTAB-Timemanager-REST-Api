package se.karingotrafiken.timemanager.rest.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import se.karingotrafiken.timemanager.rest.appmodel.exceptionmappers.AccessDeniedExceptionMapper;
import se.karingotrafiken.timemanager.rest.appmodel.exceptionmappers.ApiExceptionMapper;
import se.karingotrafiken.timemanager.rest.appmodel.exceptionmappers.ConstraintViolationExceptionMapper;
import se.karingotrafiken.timemanager.rest.appmodel.exceptionmappers.MySqlConstraintViolationExceptionMapper;
import se.karingotrafiken.timemanager.rest.resources.*;

@Component
@Configuration
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        this.property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
        this.property(ServerProperties.BV_DISABLE_VALIDATE_ON_EXECUTABLE_OVERRIDE_CHECK, true);

        // Resources
        register(BoatResource.class);
        register(EmployeeResource.class);
        register(HealthResource.class);
        register(ReportResource.class);
        register(ScheduleResource.class);
        register(ServiceCategoryResource.class);
        register(ShiftResource.class);
        register(UnionContractResource.class);
        register(UserResource.class);
        register(WithdrawalResource.class);


        // Exception mappers
        register(AccessDeniedExceptionMapper.class);
        register(ApiExceptionMapper.class);
        register(ConstraintViolationExceptionMapper.class);
        register(MySqlConstraintViolationExceptionMapper.class);

    }
}
