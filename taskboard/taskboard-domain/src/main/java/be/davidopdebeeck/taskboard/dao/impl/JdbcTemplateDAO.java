package be.davidopdebeeck.taskboard.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Implements the default behaviour for a DAO with a JDBC template
 */
public abstract class JdbcTemplateDAO
{
    protected JdbcTemplate jdbcTemplate;

    /**
     * Sets the jdbc templates of the DAO
     *
     * @param jdbcTemplate The jdbc templates of the DAO
     */
    public void setJdbcTemplate( JdbcTemplate jdbcTemplate )
    {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * @return The jdbc template of the DAO
     */
    public JdbcTemplate getJdbcTemplate()
    {
        return jdbcTemplate;
    }
}
