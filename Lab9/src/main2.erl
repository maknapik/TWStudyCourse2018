%%%-------------------------------------------------------------------
%%% @author mateusz
%%% @copyright (C) 2018, <COMPANY>
%%% @doc
%%%
%%% @end
%%% Created : 28. Nov 2018 9:59 AM
%%%-------------------------------------------------------------------
-module(main2).
-author("mateusz").

%% API
-export([main/0, a/0, b/0, c/1]).

a() ->
  io:fwrite("A sends\n"),
  c ! {"aaa"},
  a().

b() ->
  io:fwrite("B sends\n"),
  c ! {"bbb"},
  b().

c(Type) ->
  case {Type} of
    {""} ->
      receive
        {"aaa"} ->
          io:fwrite("C received aaa\n"),
          Type = "aaa";
        {"bbb"} ->
          io:fwrite("C received bbb\n"),
          Type = "bbb"
      end,
      c(Type);
    {"aaa"} ->
      receive
        aaa ->
          io:fwrite("C received aaa\n")
      end,
      c(Type);
    {"bbb"} ->
      receive
      {"bbb"} ->
      io:fwrite("C received bbb\n")
      end,
      c(Type)
  end.


main() ->
  register(c, spawn(main, c, [""])),
  spawn(main, a, []),
  spawn(main, b, []).
