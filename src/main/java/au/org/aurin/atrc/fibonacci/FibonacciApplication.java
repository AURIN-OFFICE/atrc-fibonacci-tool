package au.org.aurin.atrc.fibonacci;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class FibonacciApplication implements CommandLineRunner {
    @Value("${inputs.f0.value:0}")
    private Long f0;

    @Value("${inputs.f1.value:1}")
    private Long f1;

    @Value("${inputs.length.value:10}")
    private Integer length;

    @Value("${inputs.outputPath.value:/data/outputs/sequence.json}")
    private String outputPath;

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
        Assert.notNull(f0, "f0 must not be null");
        Assert.notNull(f1, "f1 must not be null");
        Assert.notNull(length, "length must not be null");
        Assert.isTrue(length >= 2, "length must be >= 2");
        Assert.hasText(outputPath, "outputPath must contain text");

        System.out.printf("Using the following input parameters: f0=%d, f1=%d, length=%d, outputPath=%s%n", f0, f1, length, outputPath);
        System.out.println("Creating Fibonacci sequence...");
        final var sequence = createSequence(f0, f1, length);

        System.out.println("The following sequence was calculated: " + sequence);
        System.out.println("Writing sequence to specified file: " + outputPath);

        final var objectMapper = new ObjectMapper();
        objectMapper.writeValue(new File(outputPath), sequence);

        System.out.println("Finished work.");
    }
}
