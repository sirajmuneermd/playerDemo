package com.siraj.players;

import com.siraj.players.entity.Players;
import com.siraj.players.entity.Sports;
import com.siraj.players.repository.PlayersRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class PlayersDemoApnApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlayersDemoApnApplication.class, args);


		/*ConfigurableApplicationContext conf = SpringApplication.run(PlayersDemoApnApplication.class, args);
		PlayersRepository rep= conf.getBean(PlayersRepository.class);

		Players pl = new Players("sirajmuneer@yopamil.com",7,3,"M","siraj","muneer");
		Sports sp = new Sports("BasketBall",pl);
		Sports sp1 = new Sports("Cricket",pl);
		List<Sports> ss= Arrays.asList(sp,sp1);
		pl.setSports(ss);
		rep.save(pl); */
	}

}
