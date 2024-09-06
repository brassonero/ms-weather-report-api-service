INSERT INTO airport (iata_code, name, latitude, longitude) VALUES
                                                               ('JFK', 'John F. Kennedy International Airport', 40.6413, -73.7781),
                                                               ('LAX', 'Los Angeles International Airport', 33.9416, -118.4085);

INSERT INTO ticket (origin, destination, airline, flight_num) VALUES
                                                                  ('JFK', 'LAX', 'AA', 'AA123'),
                                                                  ('LAX', 'JFK', 'DL', 'DL456');