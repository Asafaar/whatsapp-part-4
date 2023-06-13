//package com.example.whatsapp_part_4.Async;
//
//import android.os.AsyncTask;
//import android.os.Message;
//
//import com.example.whatsapp_part_4.Model.Model;
//import com.example.whatsapp_part_4.data.UserMessage;
//
//import java.net.URL;
//import java.util.List;
//import java.util.concurrent.CompletableFuture;
//
//public class AsyncTaskMessegs extends AsyncTask<Void, List<Message>, List<Message>> {
//
//    @Override
//    protected List<Message> doInBackground(Integer... integers) {
//        List<com.example.whatsapp_part_4.data.Message> list = messageDao.getMessagesById(id);
//        listmessages.setValue(list);
//        CompletableFuture<List<com.example.whatsapp_part_4.data.Message>> future = mainApiManger.getMessgesByuser(id);
//        future.thenApply(messages -> {
//            if (messages != null) {
////                messageDao.deleteAllMessages();
//                messageDao.insertAll(messages);
////                userMessageConnectDao.deleteAllMessages();
//
//                for (com.example.whatsapp_part_4.data.Message message : messages) {//add to the db connect table
//                    UserMessage userMessage = new UserMessage(id, message.getId());
//                    userMessageConnectDao.insert(userMessage);
//
//                    listmessages.setValue(messages);
//                }
//                return 1;
//            } else {
//                return -1;
//            }
//        });
//
//        return null;
//    }
//}
