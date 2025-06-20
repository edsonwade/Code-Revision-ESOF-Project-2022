/**
 * Author: vanilson muhongo
 * Date:20/06/2025
 * Time:15:16
 * Version:1
 */

package cloud.testcontainer;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;


@Testcontainers
@ExtendWith(TccTestWatcher.class)
// Este watcher é específico do Testcontainers Cloud, pode remover se não estiver a usar
public class TestcontainersCloudFirstTest {

    @Test
    void shouldCreateAndQueryPostgresWithManualDDL() throws Exception {
        try (PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:14-alpine")) {
            postgres.start();

            try (Connection conn = DriverManager.getConnection(postgres.getJdbcUrl(), postgres.getUsername(), postgres.getPassword())) {
                conn.createStatement().execute("""
                            create table guides (
                                id bigserial primary key,
                                title varchar(1023) not null,
                                url varchar(1023) not null
                            );
                            insert into guides(title, url) values
                                ('Testcontainers for Java', 'https://testcontainers.com/java'),
                                ('Testcontainers for .NET', 'https://testcontainers.com/dotnet');
                        """);

                PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM guides");
                ResultSet rs = stmt.executeQuery();
                rs.next();

                assertThat(rs.getInt(1)).isEqualTo(2);
            }
        }
    }

}
