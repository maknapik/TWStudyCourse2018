%%%-------------------------------------------------------------------
%%% @author mateusz
%%% @copyright (C) 2018, <COMPANY>
%%% @doc
%%%
%%% @end
%%% Created : 26. Nov 2018 8:35 PM
%%%-------------------------------------------------------------------
-module(main).
-author("mateusz").

-import(string, [len/1, concat/2, chr/2, substr/3, str/2, to_lower/1, to_upper/1]).

-export([main/0, add/2]).

main() ->
  io:fwrite("Hello World\n"),
  io:fwrite("~w", [var_stuff()]).

add(A,B) ->
  A + B.

var_stuff() ->
  Num = 1,
  Num.
