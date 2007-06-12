package com.bretth.osm.conduit.mysql.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.bretth.osm.conduit.ConduitRuntimeException;


/**
 * Provides iterator like behaviour for reading way segments from a database.
 * 
 * @author Brett Henderson
 */
public class WaySegmentReader extends EntityReader<WaySegment> {
	private static final String SELECT_SQL =
		"SELECT id AS way_id, segment_id, sequence_id FROM way_segments ORDER BY way_id, sequence_id";
	
	
	/**
	 * Creates a new instance.
	 * 
	 * @param host
	 *            The server hosting the database.
	 * @param database
	 *            The database instance.
	 * @param user
	 *            The user name for authentication.
	 * @param password
	 *            The password for authentication.
	 */
	public WaySegmentReader(String host, String database, String user, String password) {
		super(host, database, user, password);
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected WaySegment createNextValue(ResultSet resultSet) {
		long wayId;
		long segmentId;
		int sequenceId;
		
		try {
			wayId = resultSet.getLong("way_id");
			segmentId = resultSet.getLong("segment_id");
			sequenceId = resultSet.getInt("sequence_id");
			
		} catch (SQLException e) {
			throw new ConduitRuntimeException("Unable to read way segment fields.", e);
		}
		
		return new WaySegment(wayId, segmentId, sequenceId);
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getQuerySql() {
		return SELECT_SQL;
	} 
}