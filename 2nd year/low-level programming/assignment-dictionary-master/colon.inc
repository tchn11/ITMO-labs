%define end 0

%macro colon 2
%%next:
dq end
db %1, 0
%2:
%define end %%next
%endmacro
