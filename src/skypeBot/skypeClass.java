package skypeBot;

import com.skype.ChatMessage;
import com.skype.ChatMessageAdapter;
import com.skype.Skype;
import com.skype.SkypeException;
import com.skype.User;
import com.skype.Chat;

public class skypeClass {
    public String listenFor = "!";
    
	skypeClass() throws Exception {
		startListening();
	}
    public void SendTo(String userName, String message) {
    	try {
            Skype.chat(userName).send(message);
        } catch (SkypeException e) {
        	e.printStackTrace();
        }
    }

    public void startListening() throws Exception {
        // To prevent exiting from this program
        Skype.setDaemon(false);
         
        Skype.addChatMessageListener(new ChatMessageAdapter() {
            public void chatMessageReceived(ChatMessage received) throws SkypeException {
                if (received.getType().equals(ChatMessage.Type.SAID)) {
                	
                    // Sender
                    User sender = received.getSender(); 
                	@SuppressWarnings("unused") 
                    String senderId = sender.getId();
                    String senderName = sender.getFullName();
                    
                    // chat
                    Chat chat = received.getChat();     
                	@SuppressWarnings("unused")
                    String chatId = chat.getId();     
                    String chatName = chat.getWindowTitle(); 
                    
                    String content = received.getContent();

                    System.out.println("C:"+chatName+": "+content);
                    System.out.println("P:"+senderName+": "+content);
                   
                    /* 
                     * SendTo(chatId,"sain!");
                    	received.getSender().send(
                            "I'm working. Please, wait a moment.");
                     */
                }
            }
        });
         
        System.out.println("Auto Answering started!");
    }

}
