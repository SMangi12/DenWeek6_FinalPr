const functions = require("firebase-functions");
const admin = require("firebase-admin");
admin.initializeApp();

exports.sendChatNotification = functions.database
    .ref("/Chats/{pushId}")  // triggers whenever a new chat message is added
    .onCreate(async (snapshot, context) => {
        const messageData = snapshot.val();

        const senderName = messageData.senderName || "Someone"; // optional if you send it
        const message = messageData.message;
        const receiverId = messageData.receiver;

        // Get receiver FCM token
        const tokenSnapshot = await admin.database().ref(`/users/${receiverId}/fcmToken`).once("value");
        const token = tokenSnapshot.val();

        if (token) {
            const payload = {
                notification: {
                    title: `New message from ${senderName}`,
                    body: message,
                },
                data: {
                    senderId: messageData.sender,
                    message: message,
                },
            };

            await admin.messaging().sendToDevice(token, payload);
            console.log("Notification sent to", receiverId);
        }
    });
