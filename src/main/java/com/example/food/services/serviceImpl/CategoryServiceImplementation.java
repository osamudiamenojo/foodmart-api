package com.example.food.services.serviceImpl;

import com.example.food.dto.EmailSenderDto;
import com.example.food.exceptions.InstanceAlreadyExistsException;
import com.example.food.model.Category;
import com.example.food.pojos.models.CategoryDto;
import com.example.food.repositories.CategoryRepository;
import com.example.food.services.CategoryService;
import com.example.food.services.EmailService;
import com.example.food.util.ResponseProvider;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CategoryServiceImplementation implements CategoryService {

    private final CategoryRepository categoryRepository;
    @Override
    public String createCategory(CategoryDto categoryDto) {
        Optional<Category> newCategory = Optional.ofNullable(categoryRepository.findByCategoryName(categoryDto.getCategoryName()));
        if (newCategory.isPresent()) {
            throw new InstanceAlreadyExistsException("Category is available");
        }
            Category category = new Category();
            BeanUtils.copyProperties(categoryDto, category);
            categoryRepository.save(category);
            return "New Category Added.";
        }

    @Component
    @AllArgsConstructor
    public static class EmailServiceImpl implements EmailService {

        private JavaMailSender emailSender;
        private ResponseProvider response;
        private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
        @Override
        public void sendMail(EmailSenderDto emailSenderDto) {
            if (
                    (Objects.nonNull(emailSenderDto.getTo())) &&
                    (Objects.nonNull(emailSenderDto.getSubject())) &&
                     (Objects.nonNull(emailSenderDto.getContent()))
            ) {

                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(emailSenderDto.getTo());
                message.setSubject(emailSenderDto.getSubject());
                message.setText(emailSenderDto.getContent());
                emailSender.send(message);

                LOGGER.info("Mail has been sent");

            }
                LOGGER.error("Failed to send mail. Check the details you supplied " + String.valueOf(emailSenderDto));
        }

    }
}


