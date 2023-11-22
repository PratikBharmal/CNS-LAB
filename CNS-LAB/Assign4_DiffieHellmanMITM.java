import java.util.Scanner;
public class Assign4_DiffieHellmanMITM {
    
    // This block defines a modular exponentiation function (modPow). 
    // This function calculates (base ^ exponent) % modulo efficiently using the binary exponentiation algorithm.
     static long modPow(long base, long exponent, long modulo) {
        long result = 1;
        while (exponent > 0) {
            if (exponent % 2 == 1) {
                result = (result * base) % modulo;
            }
            base = (base * base) % modulo;
            exponent /= 2;
        }
        return result;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        // Constants (publicly known)
        final long p = 7; // Prime modulus
        final long g = 3;  // Primitive root modulo p

        // Alice's private key
        Scanner  scanner = new Scanner(System.in);
        System.out.print("Alice, enter your private key: ");
        long privateKeyAlice = scanner.nextLong();

        // Bob's private key
        System.out.print("Bob, enter your private key: ");
        long privateKeyBob = scanner.nextLong();

        // Alice computes her public key
        long publicKeyAlice = modPow(g, privateKeyAlice, p);

        // Bob computes his public key
        long publicKeyBob = modPow(g, privateKeyBob, p);

        // MITM attack (Eve)
        long interceptedPublicKeyAlice;
        long interceptedPublicKeyBob;

        // Eve intercepts the public keys
        interceptedPublicKeyAlice = publicKeyAlice;
        interceptedPublicKeyBob = publicKeyBob;

        // Attacker (Eve) replaces public keys with her own
        publicKeyAlice = interceptedPublicKeyBob;
        publicKeyBob = interceptedPublicKeyAlice;

        // Shared secret calculation
        long sharedSecretAlice = modPow(publicKeyBob, privateKeyAlice, p);
        long sharedSecretBob = modPow(publicKeyAlice, privateKeyBob, p);

        
        // Display shared secrets
        System.out.println("Shared secret computed by Alice: " + sharedSecretAlice);
        System.out.println("Shared secret computed by Bob: " + sharedSecretBob);

        // Check if shared secrets match
        if (sharedSecretAlice == sharedSecretBob) {
            System.out.println("Communication is secure. Messages are not compromised.");
        } else {
            System.out.println("MITM attack successful! Eve has intercepted the messages.");
            System.out.println("Eve's intercepted data: " + sharedSecretAlice);
        }
    }
    
}