
## About the data files

 - `dtd_files.txt`: Can be used to brute force the path of an existing DTD.
 - `xxe_payloads.md`: List of working payloads for the DTD list in the previous dictionary.
 - `dtd_files_jars.txt`: If you find a custom jars directory, you can compare the jars found against this list.
 - `dtd_files_mvn_repo.txt`: Once you have identify a maven local repository location, you can brute force for potential Maven packages.

### References

 - https://www.gosecure.net/blog/2019/07/16/automating-local-dtd-discovery-for-xxe-exploitation How to find other DTD if the list is not enough.
 - https://mohemiv.com/all/exploiting-xxe-with-local-dtd-files/ Original research to better understand how those payloads work.
