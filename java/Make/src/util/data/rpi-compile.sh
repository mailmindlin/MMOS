dd bs=512 count=$OUTPUTBLOCKCOUNT if=/dev/zero of=$OUTPUT
fdisk -b 512 -H 255 -S 63 -C $OUTPUTCYLINDERS $OUTPUT << EOF
x
c
$OUTPUTCYLINDERS
r
n
p
1
1
2
t
c

w
EOF
echo "Finished BUILDING IMAGE"