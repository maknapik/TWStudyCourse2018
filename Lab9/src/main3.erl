%%%-------------------------------------------------------------------
%%% @author mateusz
%%% @copyright (C) 2018, <COMPANY>
%%% @doc
%%%
%%% @end
%%% Created : 28. Nov 2018 9:59 AM
%%%-------------------------------------------------------------------
-module(main3).
-author("mateusz").

%% API
-export([main/0, a/1, b/1, c/0]).

a(It) ->
  io:fwrite("A sent\n"),
  c ! {"aaa", It},
  a((It + 1)).

b(It) ->
  io:fwrite("B sent\n"),
  c ! {"bbb", It},
  b((It + 1)).

c() ->
  receive
    {"aaa", It} ->
      io:fwrite("C received aaa, iteration: ~w\n", It)
  end,
  receive
    {"bbb", It} ->
      io:fwrite("C received bbb, iteration: ~w\n", It)
  end,
  c().

main() ->
  register(c, spawn(main3, c, [])),
  spawn(main3, a, [0]),
  spawn(main3, b, [0]).
