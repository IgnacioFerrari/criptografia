package edu.utn.criptografia;

import com.password4j.Argon2Function;
import com.password4j.BenchmarkResult;
import com.password4j.Hash;
import com.password4j.Password;
import com.password4j.SystemChecker;
import com.password4j.types.Argon2;

public class HashContrasena {
    public static void ejemplo(final String plainPassword) {
        Hash passwordHash = Password.hash(plainPassword)
            .addRandomSalt(8)
            .withArgon2();
        
        System.out.println(passwordHash.getResult());
        System.out.println(passwordHash.getSalt());

        System.out.println("verifica? " +
            Password.check(plainPassword, passwordHash.getResult())
            .addSalt(passwordHash.getSalt())
            .withArgon2()
        );
    }

    public static void benchmark() {
        long maxMilliseconds = 500;
        int memory = 4096;
        int threads = 2;
        int outputLength = 128;
        Argon2 type = Argon2.ID;

        BenchmarkResult<Argon2Function> result = SystemChecker.benchmarkForArgon2(maxMilliseconds, memory, threads, outputLength, type);


        int iterations = result.getPrototype().getIterations(); // 196;
        long realElapsed = result.getElapsed(); // 472
        System.out.println(String.format("iterations %d; elapsed %d", iterations, realElapsed));
    }
}
