package uz.itransition.collectin.service.core;


import io.jsonwebtoken.lang.Assert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class AbstractPageable<R extends JpaRepository> {
    private static final int DEFAULT_SIZE = 20;

    private static final int DEFAULT_PAGE = 0;

    private static final String direction = "DESC";

    private static final String SORT = "creationDate";

    public Page list(R repository){
        Assert.notNull(repository, String.format("%s must not be null", repository.getClass().getName()));
        return repository.findAll(PageRequest.of(DEFAULT_PAGE,DEFAULT_SIZE, Sort.Direction.valueOf(direction),SORT));
    }

    public Page list(R repository, PageRequest pageRequest){
        Assert.notNull(repository, String.format("%s must not be null", repository.getClass().getName()));
        return repository.findAll(pageRequest);
    }

}
