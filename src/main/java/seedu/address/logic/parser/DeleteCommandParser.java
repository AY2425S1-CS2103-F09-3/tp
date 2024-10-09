package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    //    public DeleteCommand parse1(String args) throws ParseException {
    //        try {
    //            Index index = ParserUtil.parseIndex(args);
    //            return new DeleteCommand(index);
    //        } catch (ParseException pe) {
    //            throw new ParseException(
    //                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
    //        }
    //    }

    public DeleteCommand parse(String args) throws ParseException {
        String[] trimmedArgs = args.trim().split(" ");
        ArgumentMultimap argMultiMap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_EMAIL, PREFIX_PHONE);

        // Check if the arguments consist of a single word
        if (trimmedArgs.length == 1) {
            try {
                // Try to parse it as an index first
                Index index = ParserUtil.parseIndex(args);
                return new DeleteCommand(index);
            } catch (ParseException pe) {
                // If it is not an index, handle using parseOtherAttributes
                return parseOtherAttributes(argMultiMap);
            }
        }
        return parseOtherAttributes(argMultiMap);
    }

    /**
     * Parses the arguments based on name, email and phone
     * @param argMultimap processed from parse
     * @return DeleteCommand object with the contact index to be deleted
     * @throws ParseException
     */
    public DeleteCommand parseOtherAttributes(ArgumentMultimap argMultimap) throws ParseException {
        Optional<String> optionalName = argMultimap.getValue(PREFIX_NAME);
        Optional<String> optionalPhone = argMultimap.getValue(PREFIX_PHONE);
        Optional<String> optionalEmail = argMultimap.getValue(PREFIX_EMAIL);
        if (optionalPhone.isEmpty() && optionalEmail.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
        if (optionalName.isPresent()) {
            Name name = ParserUtil.parseName(optionalName.get());
            System.out.println("finding using name and check duplicates");
            System.out.println("assuming there are duplicates");
            if (optionalPhone.isPresent()) {
                Phone phone = ParserUtil.parsePhone(optionalPhone.get());
                System.out.println("finding using phone");
            } else if (optionalEmail.isPresent()) {
                Email email = ParserUtil.parseEmail(optionalEmail.get());
                System.out.println("finding using email");
            }
        }
        return new DeleteCommand(Index.fromOneBased(1));
    }
}
