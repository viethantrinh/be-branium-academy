package net.branium.mappers;


import net.branium.domains.Order;
import net.branium.domains.Role;
import net.branium.dtos.course.CourseResponse;
import net.branium.dtos.payment.OrderResponse;
import net.branium.dtos.role.RoleRequest;
import net.branium.dtos.role.RoleResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {

}
