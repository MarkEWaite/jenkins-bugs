REM Remove all submodules from test-submodules directory

REM See https://stackoverflow.com/questions/1260748/how-do-i-remove-a-submodule

FOR /D %%T IN ("test-submodules\*") do (

    RMDIR /S/Q %%T
    git submodule deinit -f -- %%T
    RMDIR /S/Q .git\modules\%%T

    git rm -f %%T
    git commit -m "Remove submodule %%~nxT"

)
