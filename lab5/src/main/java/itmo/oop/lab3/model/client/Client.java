package itmo.oop.lab3.model.client;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

public class Client {
    @Getter
    private final String name;
    @Getter
    private final String surname;
    @Setter
    private String address;
    @Setter
    private String passport;

    public Client(String name, String surname) {
        if (name == null || surname == null)
            throw new IllegalArgumentException("Name and surname cannot be null");
        this.name = name;
        this.surname = surname;
    }

    public Optional<String> getAddress() {
        return Optional.ofNullable(address);
    }

    public Optional<String> getPassport() {
        return Optional.ofNullable(passport);
    }

    public static ClientBuilder builder() {
        return new ClientBuilder();
    }

    public static class ClientBuilder {
        private String name;
        private String surname;
        private String address;
        private String passport;

        public ClientBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ClientBuilder surname(String surname) {
            this.surname = surname;
            return this;
        }

        public ClientBuilder address(String address) {
            this.address = address;
            return this;
        }

        public ClientBuilder passport(String passport) {
            this.passport = passport;
            return this;
        }

        public Client build() {
            Client client = new Client(name, surname);
            if (address != null)
                client.setAddress(address);
            if (passport != null)
                client.setPassport(passport);
            return client;
        }
    }
}
