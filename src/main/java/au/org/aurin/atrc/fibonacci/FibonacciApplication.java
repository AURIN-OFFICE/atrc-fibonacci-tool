package au.org.aurin.atrc.fibonacci;

import au.org.aurin.atrc.fibonacci.FibonacciApplication.Inputs;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableConfigurationProperties(Inputs.class)
@RequiredArgsConstructor
public class FibonacciApplication implements CommandLineRunner {
    @NonNull
    private final Inputs inputs;

    public static void main(String[] args) {
        SpringApplication.run(FibonacciApplication.class, args);
    }

    private static List<Long> createSequence(final Long f0, final long f1, final int length) {
        Assert.isTrue(length >= 2, "length must be >= 2");

        final var sequence = new ArrayList<Long>(length);
        sequence.add(f0);
        sequence.add(f1);

        var a = f0;
        var b = f1;
        for (var i = 2; i <= length; i++) {
            final var c = calculateNext(a, b);

            sequence.add(c);

            a = b;
            b = c;
        }

        return sequence;
    }

    private static long calculateNext(final long f0, final long f1) {
        return f0 + f1;
    }

    @Override
    public void run(String... args) throws IOException {
        System.out.println("Using the following input parameters: " + inputs);
        System.out.println("Creating Fibonacci sequence...");
        final var sequence = createSequence(
                inputs.f0.value,
                inputs.f1.value,
                inputs.length.value);

        System.out.println("The following sequence was calculated: " + sequence);
        System.out.println("Writing sequence to specified file: " + inputs.outputPath);

        final var objectMapper = new ObjectMapper();
        objectMapper.writeValue(new File(inputs.outputPath), sequence);

        System.out.println("Finished work.");
    }

    public record InputsLong(
            @NonNull Long value,
            @NonNull String type) {
    }

    public record InputsInteger(
            @NonNull Integer value,
            @NonNull String type) {
    }

    @ConfigurationProperties(prefix = "inputs")
    public record Inputs(
            @NonNull InputsLong f0,
            @NonNull InputsLong f1,
            @NonNull InputsInteger length,
            @NonNull String outputPath) {
    }
}
