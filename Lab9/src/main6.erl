%%%-------------------------------------------------------------------
%%% @author mateusz
%%% @copyright (C) 2018, <COMPANY>
%%% @doc
%%%
%%% @end
%%% Created : 11. Dec 2018 4:27 PM
%%%-------------------------------------------------------------------
-module(main6).
-author("mateusz").

%% API
-export([main/0, producer/2, consumer/2, buffer/3]).

-define(N, 10).
-define(P, 3).
-define(C, 3).

producer(Buffers, Nast) ->
  Portion = rand:uniform(10),
  io:fwrite("Producer is producing ~w\n", [Portion]),
  lists:nth(Nast, Buffers) ! { producing, Portion },

  Nast2 = (Nast + 1) rem ?N,
  if Nast2 == 0 ->
    Nast3 = Nast2 + 1;
  true ->
    Nast3 = Nast2
  end,

  producer(Buffers, Nast3).

consumer(Buffers, Nast) ->
  lists:nth(Nast, Buffers) ! { consuming, self() },
  receive

    { canRemove, Portion } ->
      io:fwrite("Consumer is consuming ~w\n", [Portion]),

      Nast2 = (Nast + 1) rem ?N,
      if Nast2 == 0 ->
        Nast3 = Nast2 + 1;
        true ->
          Nast3 = Nast2
      end,

      consumer(Buffers, Nast3)

  end.

buffer(Capacity, Size, Portion) ->
  receive

    { producing, P } when Size < Capacity ->
      io:fwrite("Buffer received portion ~w\n", [P]),
      buffer(Capacity, Size + 1, P);

    { consuming, Consumer } when Size > 0 ->
      io:fwrite("Buffer is being consumed - ~w\n", [Portion]),
      Consumer ! { canRemove, Portion },
      buffer(Capacity, Size - 1, nil)

  end.

main() ->
  Buffers = [spawn(main6, buffer, [1, 0, nil]) || _ <- lists:seq(1, ?N)],
  [spawn(main6, producer, [Buffers, 1]) || _ <- lists:seq(1, ?P)],
  [spawn(main6, consumer, [Buffers, 1]) || _ <- lists:seq(1, ?C)].
