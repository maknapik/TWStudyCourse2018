%%%-------------------------------------------------------------------
%%% @author mateusz
%%% @copyright (C) 2018, <COMPANY>
%%% @doc
%%%
%%% @end
%%% Created : 05. Dec 2018 9:51 AM
%%%-------------------------------------------------------------------
-module(main5).
-author("mateusz").

%% API
-export([main/0, producer/1, consumer/1, buffer/2]).

producer(Buffer) ->
  Buffer ! { producing, self() },
  receive
    { canInsert } ->
      io:fwrite("Producer is producing\n"),
      producer(Buffer);
    { isFull } ->
      io:fwrite("Buffer is full\n"),
      producer(Buffer)
  end.

consumer(Buffer) ->
  Buffer ! { consuming, self() },
  receive
    { canRemove } ->
      io:fwrite("Consumer is consuming\n"),
      consumer(Buffer);
    { isEmpty } ->
      io:fwrite("Buffer is empty\n"),
      consumer(Buffer)
  end.

buffer(Capacity, Size) ->
  receive
    { producing, Producer } ->
      if Size < Capacity ->
        io:fwrite("Buffer received portion\n"),
        Producer ! { canInsert },
        buffer(Capacity, Size + 1);
      true ->
        io:fwrite("Buffer is full\n"),
        Producer ! { isFull },
        buffer(Capacity, Size)
      end;
    { consuming, Consumer } ->
      if Size > 0 ->
        io:fwrite("Buffer is being consumed\n"),
        Consumer ! { canRemove },
        buffer(Capacity, Size - 1);
      true ->
        io:fwrite("Buffer is empty\n"),
        Consumer ! { isEmpty },
        buffer(Capacity, Size)
      end
  end.


main() ->
  Buffer = spawn(main5, buffer, [10, 0]),
  spawn(main5, producer, [Buffer]),
  spawn(main5, consumer, [Buffer]).

