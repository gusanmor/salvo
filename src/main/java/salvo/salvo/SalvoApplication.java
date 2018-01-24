package salvo.salvo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
public class SalvoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SalvoApplication.class, args);
    }


//    @Bean
//    public CommandLineRunner initData(PlayerRepository repository) {
//        return (args) -> {
//            // save a couple of customers
//            repository.save(new Player("kim_bauer@gmail.com"));
//            repository.save(new Player("t.almeida@ctu.gov"));
//
//        };
//    }

    @Bean
    public CommandLineRunner initData(GameRepository repository) {
        return (args) -> {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String date = sdf.format(new Date());
//            System.out.println(date); //15/10/2013
//            String horaSinSubstring = sdf.format(new Date());
            repository.save(new Game(date));
            repository.save(new Game(date));
//            repository.save(new Game(horaSinSubstring));

        };
    }


}
