package tests;

import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tests.generators.GeneratePerson;
import tests.models.Address;
import tests.models.Person;

import java.util.List;

public class TestGenerator {
    public List<String> names = List.of("Ann", "Corry");

    @Test
    public void faker() {
        GeneratePerson generatedPerson = new GeneratePerson();

        Person person = generatedPerson.getPerson();

        Assertions.assertTrue(person.getAge() >= 10 && person.getAge() <= 99);
    }

    @Test
    public void instancio() {
        Person person = Instancio.of(Person.class)
                .ignore(Select.field(Person::getId))
                .generate(Select.field(Person::getName), name -> name.oneOf(names))
                .create();

        Assertions.assertTrue(names.contains(person.getName()));
    }

    @Test
    public void instancioList() {
        List<Person> persons = Instancio.ofList(Person.class)
                .size(5)
                .set(Select.field(Address::getCity), "New York")
                .generate(Select.field(Person::getAge), age -> age.ints().range(18, 35))
                .generate(Select.field(Person::getName), name -> name.string().prefix("@"))
                .ignore(Select.field(Person::getId))
                .create();

        Assertions.assertFalse(persons.isEmpty());
        Assertions.assertTrue(persons.stream()
                .allMatch(person -> "New York".equals(person.getAddress().getCity())));
    }

}