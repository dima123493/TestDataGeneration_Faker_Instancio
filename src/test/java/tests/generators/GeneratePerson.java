package tests.generators;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tests.models.Address;
import tests.models.Gender;
import tests.models.Person;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeneratePerson {
    private Person person;

    public Person getPerson() {
        Faker faker = new Faker();

        Address address = Address.builder()
                .country(faker.country().name())
                .city(faker.address().city())
                .street(faker.address().streetAddress())
                .houseNumber(faker.random().nextInt(1, 1000))
                .flatNumber(faker.random().nextInt(1, 1000))
                .build();

        return Person.builder()
                .name(faker.funnyName().name())
                .age(faker.random().nextInt(10, 99))
                .gender(getGender(faker.random().nextBoolean()))
                .address(address)
                .build();
    }

    private Gender getGender(boolean value) {
        return value ? Gender.MALE : Gender.FEMALE;
    }

}
