package org.behrang.telecom.repository;

import lombok.RequiredArgsConstructor;
import org.behrang.telecom.entity.PhoneNumber;
import org.behrang.telecom.properties.PhoneNumberRepositoryProperties;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PhoneNumberRepository {

    private final PhoneNumberRepositoryProperties properties;

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public List<PhoneNumber> findAll(final int offset, final int limit) {
        final var sql = properties.getQueries().getFindAllPhoneNumbers();

        final var params = new MapSqlParameterSource()
                .addValue("limit", limit)
                .addValue("offset", offset);

        return jdbcTemplate.query(sql, params, (rs, rowNum) -> {
            final var mapped = new PhoneNumber();
            mapped.setPhoneNumber(rs.getString(1));
            return mapped;
        });
    }

    public long countAll() {
        final var sql = properties.getQueries().getCountAll();

        //noinspection ConstantConditions
        return jdbcTemplate.queryForObject(sql, Collections.emptyMap(), Long.class);
    }
}
