package cdb.mapper;

import org.springframework.data.domain.Page;

import java.util.function.Function;

/**
 * Created by vkarassouloff on 26/05/17.
 */
public class MapperPage {

    /**
     * @param page a
     * @param f a
     * @param <A> a
     * @param <B> a
     * @return
     */
    public static <A, B> Page<B> pageMapping(Page<A> page, Function<A, B> f) {
        return page.map(a -> f.apply(a));
    }


}
