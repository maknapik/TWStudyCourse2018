%%%-------------------------------------------------------------------
%%% @author mateusz
%%% @copyright (C) 2018, <COMPANY>
%%% @doc
%%%
%%% @end
%%% Created : 28. Nov 2018 9:59 AM
%%%-------------------------------------------------------------------
-module(main4).
-author("mateusz").

%% API
-export([main/0, a/0, b/0, c/0]).

a() ->
  io:fwrite("A sent\n"),
  c ! {"aaa"},
  a().

b() ->
  io:fwrite("B sent\n"),
  c ! {"bbb"},
  b().

c() ->
  receive
    {word} ->
      io:fwrite("C received ~S\n", word)
  end,
  c().

main() ->
  register(c, spawn(main4, c, [])),
  spawn(main4, a, []),
  spawn(main4, b, []).
