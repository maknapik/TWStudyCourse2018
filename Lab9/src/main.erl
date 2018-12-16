%%%-------------------------------------------------------------------
%%% @author mateusz
%%% @copyright (C) 2018, <COMPANY>
%%% @doc
%%%
%%% @end
%%% Created : 28. Nov 2018 9:59 AM
%%%-------------------------------------------------------------------
-module(main).
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
    {"aaa"} ->
      io:fwrite("C received aaa\n")
  end,
  receive
    {"bbb"} ->
      io:fwrite("C received bbb\n")
  end,
  c().

main() ->
  register(c, spawn(main, c, [])),
  spawn(main, a, []),
  spawn(main, b, []).
