package config;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DataSetup {

    public void createTablesAndStoredProcedures() throws SQLException {
        String createTablePersonsSql = "CREATE TABLE IF NOT EXISTS persons " +
                "(id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(50), age INT)";

        String createTableAthletesSql = "CREATE TABLE IF NOT EXISTS athletes " +
                "(athleteId INT PRIMARY KEY, salary DOUBLE, bonusPerTenThousandLikes DOUBLE, socialMediaFollowers INT, FOREIGN KEY (athleteId) REFERENCES persons(id))";

        String createTableCustomersSql = "CREATE TABLE IF NOT EXISTS customers " +
                "(customerId INT PRIMARY KEY, balance DOUBLE, FOREIGN KEY (customerId) REFERENCES persons(id))";

        String createTableLocationsSql = "CREATE TABLE IF NOT EXISTS locations " +
                "(locationId INT PRIMARY KEY AUTO_INCREMENT, countryName VARCHAR(50), cityName VARCHAR(50), latitude DOUBLE, longitude DOUBLE)";

        String createTableGymsSql = "CREATE TABLE IF NOT EXISTS gyms " +
                "(gymId INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(50), description VARCHAR(255), capacity INT, locationId INT, FOREIGN KEY (locationId) REFERENCES locations(locationId))";

        String createTableFitnessChallengesSql = "CREATE TABLE IF NOT EXISTS fitness_challenges " +
                "(challengeId INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(50), description VARCHAR(255), points INT)";

        String createTableCustomerFitnessChallengesSql = "CREATE TABLE IF NOT EXISTS customer_fitness_challenges " +
                "(customerId INT, challengeId INT, PRIMARY KEY (customerId, challengeId), " +
                "FOREIGN KEY (customerId) REFERENCES customers(customerId), " +
                "FOREIGN KEY (challengeId) REFERENCES fitness_challenges(challengeId))";

        String createTableProductsSql = "CREATE TABLE IF NOT EXISTS products " +
                "(productId INT PRIMARY KEY AUTO_INCREMENT, price DOUBLE, code VARCHAR(50))";

        String createTableGymMembershipsSql = "CREATE TABLE IF NOT EXISTS gym_memberships " +
                "(membershipId INT PRIMARY KEY AUTO_INCREMENT, gymId INT, durationInMonths INT, productId INT, " +
                "FOREIGN KEY (gymId) REFERENCES gyms(gymId), " +
                "FOREIGN KEY (productId) REFERENCES products(productId))";

        String createTableCustomerMembershipsSql = "CREATE TABLE IF NOT EXISTS customer_memberships " +
                "(customerId INT, membershipId INT, PRIMARY KEY (customerId, membershipId), " +
                "FOREIGN KEY (customerId) REFERENCES customers(customerId), " +
                "FOREIGN KEY (membershipId) REFERENCES gym_memberships(membershipId))";

        String createTableOrdersSql = "CREATE TABLE IF NOT EXISTS orders " +
                "(orderId INT PRIMARY KEY AUTO_INCREMENT, customerId INT, date DATE, price DOUBLE, " +
                "FOREIGN KEY (customerId) REFERENCES customers(customerId))";

        String createTableOrderProductsSql = "CREATE TABLE IF NOT EXISTS order_products " +
                "(orderId INT, productId INT, PRIMARY KEY (orderId, productId), " +
                "FOREIGN KEY (orderId) REFERENCES orders(orderId), " +
                "FOREIGN KEY (productId) REFERENCES products(productId))";

        String createTableEventsSql = "CREATE TABLE IF NOT EXISTS events " +
                "(eventId INT PRIMARY KEY AUTO_INCREMENT, startDate DATE, endDate DATE, name VARCHAR(50), description VARCHAR(255), capacity INT, locationId INT, " +
                "FOREIGN KEY (locationId) REFERENCES locations(locationId))";

        String createTableInvestorsSql = "CREATE TABLE IF NOT EXISTS investors " +
                "(investorId INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(50), contractValue DOUBLE)";

        String createTableCompetitionsSql = "CREATE TABLE IF NOT EXISTS competitions " +
                "(competitionId INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(50), date DATE, location VARCHAR(255))";

        String createTableCompetitionAthletesSql = "CREATE TABLE IF NOT EXISTS competition_athletes " +
                "(competitionId INT, athleteId INT, PRIMARY KEY (competitionId, athleteId), " +
                "FOREIGN KEY (competitionId) REFERENCES competitions(competitionId), " +
                "FOREIGN KEY (athleteId) REFERENCES athletes(athleteId))";

        String[] createStatements = {
                createTablePersonsSql,
                createTableAthletesSql,
                createTableCustomersSql,
                createTableLocationsSql,
                createTableGymsSql,
                createTableFitnessChallengesSql,
                createTableCustomerFitnessChallengesSql,
                createTableProductsSql,
                createTableGymMembershipsSql,
                createTableCustomerMembershipsSql,
                createTableOrdersSql,
                createTableOrderProductsSql,
                createTableEventsSql,
                createTableInvestorsSql,
                createTableCompetitionsSql,
                createTableCompetitionAthletesSql
        };

        Connection databaseConnection = DatabaseConfiguration.getConnection();
        Statement stmt = databaseConnection.createStatement();

        for (String sql : createStatements) {
            stmt.execute(sql);
        }

        // Create stored procedures
        String deleteProcedurePersons = "DROP PROCEDURE IF EXISTS INSERT_PERSON;";
        String createStoredProcedurePersons = "CREATE PROCEDURE INSERT_PERSON(OUT id INT, IN name VARCHAR(50), IN age INT) " +
                "BEGIN " +
                "INSERT INTO persons(name, age) VALUES (name, age); " +
                "SET id = LAST_INSERT_ID(); " +
                "END";

        String deleteProcedureAthletes = "DROP PROCEDURE IF EXISTS INSERT_ATHLETE;";
        String createStoredProcedureAthletes = "CREATE PROCEDURE INSERT_ATHLETE(OUT athleteId INT, IN name VARCHAR(50), IN age INT, IN salary DOUBLE, IN bonusPerTenThousandLikes DOUBLE, IN socialMediaFollowers INT) " +
                "BEGIN " +
                "CALL INSERT_PERSON(@id, name, age); " +
                "INSERT INTO athletes(athleteId, salary, bonusPerTenThousandLikes, socialMediaFollowers) VALUES (@id, salary, bonusPerTenThousandLikes, socialMediaFollowers); " +
                "SET athleteId = @id; " +
                "END";

        String deleteProcedureCustomers = "DROP PROCEDURE IF EXISTS INSERT_CUSTOMER;";
        String createStoredProcedureCustomers = "CREATE PROCEDURE INSERT_CUSTOMER(OUT customerId INT, IN name VARCHAR(50), IN age INT, IN balance DOUBLE) " +
                "BEGIN " +
                "CALL INSERT_PERSON(@id, name, age); " +
                "INSERT INTO customers(customerId, balance) VALUES (@id, balance); " +
                "SET customerId = @id; " +
                "END";

        String deleteProcedureCustomerFitnessChallenges = "DROP PROCEDURE IF EXISTS INSERT_CUSTOMER_FITNESS_CHALLENGE;";
        String createStoredProcedureCustomerFitnessChallenges = "CREATE PROCEDURE INSERT_CUSTOMER_FITNESS_CHALLENGE(IN customerId INT, IN challengeId INT) " +
                "BEGIN " +
                "INSERT INTO customer_fitness_challenges(customerId, challengeId) VALUES (customerId, challengeId); " +
                "END";

        String deleteProcedureProduct = "DROP PROCEDURE IF EXISTS INSERT_PRODUCT;";
        String createStoredProcedureProduct = "CREATE PROCEDURE INSERT_PRODUCT(OUT productId INT, IN price DOUBLE, IN code VARCHAR(50)) " +
                "BEGIN " +
                "INSERT INTO products(price, code) VALUES (price, code); " +
                "SET productId = LAST_INSERT_ID(); " +
                "END";

        String deleteProcedureGymMembership = "DROP PROCEDURE IF EXISTS INSERT_GYM_MEMBERSHIP;";
        String createStoredProcedureGymMembership = "CREATE PROCEDURE INSERT_GYM_MEMBERSHIP(OUT membershipId INT, IN gymId INT, IN durationInMonths INT, IN price DOUBLE, IN code VARCHAR(50)) " +
                "BEGIN " +
                "CALL INSERT_PRODUCT(@productId, price, code); " +
                "INSERT INTO gym_memberships(gymId, durationInMonths, productId) VALUES (gymId, durationInMonths, @productId); " +
                "SET membershipId = LAST_INSERT_ID(); " +
                "END";

        String deleteProcedureOrder = "DROP PROCEDURE IF EXISTS INSERT_ORDER;";
        String createStoredProcedureOrder = "CREATE PROCEDURE INSERT_ORDER(OUT orderId INT, IN customerId INT, IN date DATE, IN price DOUBLE) " +
                "BEGIN " +
                "INSERT INTO orders(customerId, date, price) VALUES (customerId, date, price); " +
                "SET orderId = LAST_INSERT_ID(); " +
                "END";

        String deleteProcedureOrderProduct = "DROP PROCEDURE IF EXISTS INSERT_ORDER_PRODUCT;";
        String createStoredProcedureOrderProduct = "CREATE PROCEDURE INSERT_ORDER_PRODUCT(IN orderId INT, IN productId INT) " +
                "BEGIN " +
                "INSERT INTO order_products(orderId, productId) VALUES (orderId, productId); " +
                "END";

        String deleteProcedureLocation = "DROP PROCEDURE IF EXISTS INSERT_LOCATION;";
        String createStoredProcedureLocation = "CREATE PROCEDURE INSERT_LOCATION(OUT locationId INT, IN countryName VARCHAR(50), IN cityName VARCHAR(50), IN latitude DOUBLE, IN longitude DOUBLE) " +
                "BEGIN " +
                "INSERT INTO locations(countryName, cityName, latitude, longitude) VALUES (countryName, cityName, latitude, longitude); " +
                "SET locationId = LAST_INSERT_ID(); " +
                "END";

        stmt.execute(deleteProcedurePersons);
        stmt.execute(createStoredProcedurePersons);

        stmt.execute(deleteProcedureAthletes);
        stmt.execute(createStoredProcedureAthletes);

        stmt.execute(deleteProcedureCustomers);
        stmt.execute(createStoredProcedureCustomers);

        stmt.execute(deleteProcedureCustomerFitnessChallenges);
        stmt.execute(createStoredProcedureCustomerFitnessChallenges);

        stmt.execute(deleteProcedureProduct);
        stmt.execute(createStoredProcedureProduct);

        stmt.execute(deleteProcedureGymMembership);
        stmt.execute(createStoredProcedureGymMembership);

        stmt.execute(deleteProcedureOrder);
        stmt.execute(createStoredProcedureOrder);

        stmt.execute(deleteProcedureOrderProduct);
        stmt.execute(createStoredProcedureOrderProduct);

        stmt.execute(deleteProcedureLocation);
        stmt.execute(createStoredProcedureLocation);

        stmt.close();
        databaseConnection.close();
    }

    public static void main(String[] args) {
        DataSetup dataSetup = new DataSetup();
        try {
            dataSetup.createTablesAndStoredProcedures();
            System.out.println("Tables and stored procedures created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
