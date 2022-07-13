package org.behrang.telecom.repository;

import lombok.RequiredArgsConstructor;
import org.behrang.telecom.entity.Customer;
import org.behrang.telecom.properties.CustomerRepositoryProperties;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CustomerRepository {

    private final CustomerRepositoryProperties properties;

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public Customer findById(final UUID id) {
        final var sql = properties.getQueries().getFindById();

        final var params =
                new MapSqlParameterSource().addValue(CustomerConstants.ParameterNames.ID, id);

        return jdbcTemplate.queryForObject(sql, params, new CustomerMapper());
    }

    public static class CustomerMapper implements RowMapper<Customer> {

        @Override
        public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
            final var model = new Customer();

            model.setId(UUID.fromString(rs.getString(CustomerConstants.ColumnNames.ID)));
            model.setName(rs.getString(CustomerConstants.ColumnNames.NAME));

            return model;
        }
    }

}
