package biblio_server.mqtt.sender;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttSender {

	MqttClient client;
	
	public void sendMessage(String message) {
		try {
			client = new MqttClient("tcp://localhost:1883", "library");
			client.connect();
			MqttMessage toSend = new MqttMessage();
			toSend.setPayload(message.getBytes());
			client.publish("library/send", toSend);
			client.disconnect();
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}
}
