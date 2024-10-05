package net.branium.services.impl;

import lombok.RequiredArgsConstructor;
import net.branium.mappers.CourseMapper;
import net.branium.repositories.CourseRepository;
import net.branium.services.CourseService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepo;
    private final CourseMapper courseMapper;

}
