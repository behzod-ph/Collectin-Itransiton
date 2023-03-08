package uz.itransition.collectin.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.itransition.collectin.entity.Collection;
import uz.itransition.collectin.entity.Comment;
import uz.itransition.collectin.payload.response.collection.CollectionResponse;
import uz.itransition.collectin.payload.response.comment.CommentResponse;
import uz.itransition.collectin.payload.response.comment.CommentSearchResponse;

@Component
public class AppComponents {

    @Bean
    public ModelMapper modelMapper() {
        final ModelMapper modelMapper = new ModelMapper();

        //CommentResponse
        final TypeMap<Comment, CommentResponse> typeMap = modelMapper
                .createTypeMap(Comment.class, CommentResponse.class);
        typeMap.addMappings(mapping -> mapping.map(Comment::getUser, CommentResponse::setCommentAuthor));
        typeMap.addMapping(src -> src.getItem().getId(), CommentResponse::setItemId);

        //CollectionResponse
        final TypeMap<Collection, CollectionResponse> typeMap1 =
                modelMapper.createTypeMap(Collection.class, CollectionResponse.class);
        typeMap1.addMappings(mapping -> mapping.map(Collection::getUser, CollectionResponse::setUserResponse));
        typeMap1.addMapping(source -> source.getTopic().getName(), CollectionResponse::setTopic);

        //CollectionSearch
        final TypeMap<Comment, CommentSearchResponse> typeMap2 = modelMapper.createTypeMap(Comment.class, CommentSearchResponse.class);
        typeMap2.addMappings(mapper -> mapper.map(src -> src.getItem().getName(), CommentSearchResponse::setItemName));
        typeMap2.addMappings(mapper -> mapper.map(src -> src.getItem().getId(), CommentSearchResponse::setItemId));

        modelMapper.getConfiguration().setSkipNullEnabled(true);
        return modelMapper;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
