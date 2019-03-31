package tutoringfx_jrowan;

/**
 *
 * @author John Rowan JR897843@wcupa.edu
 * description: this class is used in the validation of inputs being thrown will
 *              be caught and the message will usually be put into an Alert dialog
 */
public class ExpectedException extends Exception {
  ExpectedException(String message) {
    super(message);
  }
}