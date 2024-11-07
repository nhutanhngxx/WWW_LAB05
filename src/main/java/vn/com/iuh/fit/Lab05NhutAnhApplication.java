package vn.com.iuh.fit;

import com.neovisionaries.i18n.CountryCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import vn.com.iuh.fit.backend.models.Address;
import vn.com.iuh.fit.backend.models.Candidate;
import vn.com.iuh.fit.backend.repositories.AddressRepository;
import vn.com.iuh.fit.backend.repositories.CandidatesRepository;

import java.time.LocalDate;
import java.util.Random;

@SpringBootApplication
public class Lab05NhutAnhApplication {
    @Autowired
    private CandidatesRepository candidatesRepository;
    @Autowired
    private AddressRepository addressRepository;

    public static void main(String[] args) {
        SpringApplication.run(Lab05NhutAnhApplication.class, args);
    }

    public static Short mapToShort(CountryCode countryCode) {
        switch (countryCode) {
            case VN:
                return 1; // Gán giá trị Short cho Việt Nam
            case US:
                return 2; // Gán giá trị Short cho Mỹ
            default:
                return null;
        }
    }

//    Ham nay dung de tao du lieu ban dau

//    @Bean
//    CommandLineRunner initData(){
//        return args -> {
//            Random rnd =new Random();
//            for (int i = 1; i < 1000; i++) {
//                Address add = new Address("Quang Trung","HCM", mapToShort(CountryCode.VN), rnd.nextInt(70000,80000) + "");
//                addressRepository.save(add);
//                Candidate can = new Candidate(LocalDate.of(1998,rnd.nextInt(1,13),rnd.nextInt(1,29)),
//                                            "email_"+ i +"@gmail.com",
//                                            "Name " + i,
//                                            rnd.nextLong(1111111111L,9999999999L)+"",
//                                            add);
//                candidatesRepository.save(can);
//                System.out.println("Added: " +can);
//            }
//        };
//    }
}
