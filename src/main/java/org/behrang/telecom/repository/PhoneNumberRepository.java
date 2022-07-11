package org.behrang.telecom.repository;

import lombok.RequiredArgsConstructor;
import org.behrang.telecom.entity.PhoneNumber;
import org.behrang.telecom.properties.PhoneNumberRepositoryProperties;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.behrang.telecom.repository.CommonParameterNames.LIMIT;
import static org.behrang.telecom.repository.CommonParameterNames.OFFSET;
import static org.behrang.telecom.repository.PhoneNumberColumns.CUSTOMER_ID;
import static org.behrang.telecom.repository.PhoneNumberColumns.ID;
import static org.behrang.telecom.repository.PhoneNumberColumns.IS_ACTIVE;
import static org.behrang.telecom.repository.PhoneNumberColumns.PHONE_NUMBER;


@Repository
@RequiredArgsConstructor
public class PhoneNumberRepository {

    private final PhoneNumberRepositoryProperties properties;

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public List<PhoneNumber> findAll(final int offset, final int limit) {
        final var sql = properties.getQueries().getFindAllPhoneNumbers();

        final var params = new MapSqlParameterSource()
                .addValue(LIMIT, limit)
                .addValue(OFFSET, offset);

        return jdbcTemplate.query(sql, params, new PhoneNumberMapper());
    }

    public long countAll() {
        final var sql = properties.getQueries().getCountAll();

        //noinspection ConstantConditions
        return jdbcTemplate.queryForObject(sql, Collections.emptyMap(), Long.class);
    }

    public boolean existsByPhoneNumber(final String phoneNumber) {
        final var sql = properties.getQueries().getCountByPhoneNumber();

        final var params = new MapSqlParameterSource()
                .addValue(PHONE_NUMBER, phoneNumber);

        final var count = jdbcTemplate.queryForObject(sql, params, Integer.class);

        //noinspection ConstantConditions
        return count != 0;
    }

    public PhoneNumber findByPhoneNumber(final String phoneNumber) {
        final var sql = properties.getQueries().getFindByPhoneNumber();

        final var params =
                new MapSqlParameterSource().addValue(PHONE_NUMBER, phoneNumber);

        return jdbcTemplate.queryForObject(sql, params, new PhoneNumberMapper());
    }

    public int activateByPhoneNumber(final String phoneNumber) {
        final var sql = properties.getQueries().getActivateByPhoneNumber();

        final var params = new MapSqlParameterSource()
                .addValue(PHONE_NUMBER, phoneNumber);

        return jdbcTemplate.update(sql, params);
    }

    public static class PhoneNumberMapper implements RowMapper<PhoneNumber> {

        @Override
        public PhoneNumber mapRow(ResultSet rs, int rowNum) throws SQLException {
            final var model = new PhoneNumber();

            model.setId(UUID.fromString(rs.getString(ID)));
            model.setCustomerId(UUID.fromString(rs.getString(CUSTOMER_ID)));
            model.setPhoneNumber(rs.getString(PHONE_NUMBER));
            model.setActive(rs.getBoolean(IS_ACTIVE));

            return model;
        }
    }
}
