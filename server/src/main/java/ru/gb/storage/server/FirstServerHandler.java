package ru.gb.storage.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import ru.gb.storage.commons.message.DataMessage;
import ru.gb.storage.commons.message.AuthMessage;
import ru.gb.storage.commons.message.Message;
import ru.gb.storage.commons.message.TextMessage;

public class FirstServerHandler extends SimpleChannelInboundHandler<Message> {

    @Override
    public void channelActive(ChannelHandlerContext ctx)  {
        System.out.println("New active channel");
        TextMessage answer = new TextMessage();
        answer.setText("Successfully connection");
        ctx.writeAndFlush(answer);    }



    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        if(msg instanceof TextMessage){
            TextMessage message = (TextMessage) msg;
            System.out.println("incoming text message " + message.getText());
            ctx.writeAndFlush(msg);
        }
        if(msg instanceof DataMessage){
            DataMessage message = (DataMessage) msg;
            System.out.println("incoming data message " + message.getData());
            ctx.writeAndFlush(msg);
        }

        if(msg instanceof AuthMessage){
            AuthMessage message = (AuthMessage) msg;
            System.out.println(message.getLogin());
            System.out.println(message.getPassword());
            ctx.writeAndFlush(msg);
        }

    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)  {
        cause.printStackTrace();
        ctx.close();
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println("client disconnect");

    }
}