import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Class representing a contact, which is a file containing a number of
 * 'fields' which are themselves made up of field names and field contents.
 * This class contains methods to create, modify and delete contacts and
 * their fields.
 * 
 * @version Sprint 1 V1.0
 */
public class Contact{
	
	private File contactFile;
	private ArrayList<String> fieldNames;
	private ArrayList<String> fieldContents;
	
	/**
	* Constructor which takes a text file and reads each line as a field of the
	* form fieldName:fieldContents.
	* Each field name and its contents are added to their appropriate ArrayLists.
	*/
	public Contact(File contactFile){
		this.contactFile = contactFile;
		
	}
	
	/**
	 * Deletes the contact file.
	 * 
	 * @return Whether or not the deletion operation was successful.
	 */
	public boolean deleteContact(){
		boolean wasSuccessful = false;
		
		wasSuccessful = contactFile.delete();
		
		return wasSuccessful;
	}
	
	/**
	 * Given the name and contents of a field, adds them to the
	 * ArrayLists, then updates the contact file. This effectively
	 * 'adds' the field to the contact.
	 * 
	 * @param fieldName The field's name.
	 * @param fieldContents The contents of the field.
	 * @return whether or not the field addition operation was successful.
	 */
	public boolean addField(String fieldName, String fieldContent){
		boolean wasSuccessful = false;
		
		fieldNames.add(fieldName);
		fieldContents.add(fieldContent);
		
		wasSuccessful = updateContactFile();
		
		return wasSuccessful;
	}
	
	/**
	 * Given the name of the field to delete, this method removes that field's
	 * name and contents from the appropriate ArrayLists, then updates the
	 * contact file. 
	 * 
	 * @param fieldName The name of the field to be deleted.
	 * @return whether or not the operation was successful.
	 */
	public boolean removeField(String nameOfFieldToDelete){
		boolean wasSuccessful = false;
		
		if(getFieldIndexByName(nameOfFieldToDelete) == -1 || getFieldIndexByName(nameOfFieldToDelete) >= fieldContents.size()){
			fieldContents.remove(getFieldIndexByName(nameOfFieldToDelete));
			fieldNames.remove(getFieldIndexByName(nameOfFieldToDelete));
			wasSuccessful = updateContactFile();
		}
		else{
			wasSuccessful = false;
		}
		
		return wasSuccessful;
	}
	
	/**
	 * Given the name of a field and a value to change that field's contents to,
	 * this method modifies that field's contents to the given contents, then
	 * updates the contact file.
	 * 
	 * @param nameOfFieldToModify Name of the fields whose contents needs modification.
	 * @param newFieldContents  
	 * @return Whether or not the operation was successful.
	 */
	public boolean modifyField(String nameOfFieldToModify, String newFieldContents){
		boolean wasSuccessful = false;
		
		if(getFieldIndexByName(nameOfFieldToModify) == -1 || getFieldIndexByName(nameOfFieldToModify) >= fieldContents.size()){
			fieldContents.set(getFieldIndexByName(nameOfFieldToModify), newFieldContents);
			wasSuccessful = updateContactFile();
		}
		else{
			wasSuccessful = false;
		}
				
		return wasSuccessful;
	}
	
	/**
	 * Given the name of a field, this method returns that field's index in
	 * the ArrayList fieldNames. If a field with that name does not exist,
	 * -1 is returned.
	 * 
	 * @param fieldName the name of the fields whose index is to be searched for.
	 * @return the index of the field if it exists, and -1 if it does not.
	 */
	public int getFieldIndexByName(String fieldName){
		int fieldIndex = -1;
		
		for (int i = 0; i < fieldNames.size(); i++){
			if (fieldName == fieldNames.get(i)){
				fieldIndex = i;
				break;
			}
			else {
			}
		}
		
		return fieldIndex;
	}
	
	/**
	 * Writes to the contact's file all fields, in the form fieldName:fieldContents,
	 * with each field taking up its own line.
	 * 
	 * @return whether or not the operation was successful.
	 */
	public boolean updateContactFile(){
		boolean wasSuccessful = false;
		PrintWriter contactWriter = null;
		
		try {
			//Initialises the contact file writer
			contactWriter = new PrintWriter(contactFile);
			
			//Writes each field to the file
			for (int fieldNumber = 0; fieldNumber < fieldNames.size(); fieldNumber++){
				contactWriter.println(fieldNames.get(fieldNumber) + ":" + fieldContents.get(fieldNumber));
			}
			
			//If the method reaches this point, it has successfully completed the writing operation.
			wasSuccessful = true;
		}
		catch (FileNotFoundException ex){
			System.err.println("Could not write to specified note file because the file could not be found.");
		}
		finally {
			//Closes the file writer
			contactWriter.close();
		}
		
		return wasSuccessful;
	}
}